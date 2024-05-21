import type { File } from "formidable";
import { URL, amqpConnect } from "./mq";
import fs from "fs";
import { randomUUID } from "crypto";

let connection = await amqpConnect(URL);

export function postImageSync(
  avatar: File,
  onImagePathReceived: (path: string) => void
) {
  // Check mime type and only allow image/*
  let mime = avatar.mimetype
  console.log("Mime type is ", mime);
  let allowedMime = /image\/(jpeg|png)/

  if (!mime || !allowedMime.test(mime)) {
    throw new Error(`Mime type of [${mime}] not allowed.`);
  }

  connection.createChannel().then(channel => {
    const mediaQueue = "q_media"
    const imagePathQueue = "q_media_path"
    const correlationId = randomUUID();

    channel.assertQueue(imagePathQueue).then((q => {
      // Listen for replies
      channel.consume(q.queue, (message) => {
        if (message?.properties.correlationId === correlationId) {
          onImagePathReceived(message.content.toString());
          channel.close();
        }
      }, {
        noAck: false
      });
    }));

    // Send Image
    const image = fs.readFileSync(avatar.filepath);
    channel.sendToQueue(mediaQueue, image, {
      replyTo: imagePathQueue,
      correlationId: correlationId,
      contentType: mime
    });
  });
}