import express, { type Express, type NextFunction, type Request, type Response } from "express";
import { PrismaClient } from '@prisma/client'
import formidable, { type Fields, type Files } from "formidable"
import { postImageSync } from "./lib/consumer";

const app: Express = express();
const port = 3000;
const prisma = new PrismaClient();
const basePath = "/portfolio/projects"

app.get(`${basePath}/`, async (req: Request, res: Response) => {
  const projects = await prisma.project.findMany();
  res.setHeader("content-type", "application/json");
  res.status(200).send(JSON.stringify({
    projects: projects
  }));
});

app.post(`${basePath}/create`, (req: Request, res: Response, next: NextFunction) => {
  let form = formidable({
    allowEmptyFiles: true,
    minFileSize: 0,
  })

  form.parse(req, (err: Error, fields: Fields, files: Files) => {
    if (err) {
      next(err);
      res.sendStatus(400);
      return;
    }

    const title = fields["title"]?.[0];
    const description = fields["description"]?.[0];

    if (!title) {
      res.setHeader("content-type", "application/json")
      res.status(400).send(JSON.stringify({
        message: "Required fields not filled"
      }));
      return;
    }

    prisma.project.create({
      data: {
        title: title,
        description: description
      }
    }).then(newProject => {
      const avatar = files["avatar"];
      
      if (avatar && avatar.length >= 1 && avatar[0].mimetype !== "application/octet-stream") {
        postImageSync(avatar[0], async (imagePath) => {
          console.log("Reply received! ", imagePath);
          await prisma.project.update({
            data: {
              imagePath: imagePath
            },
            where: {
              id: newProject.id
            }
          });
        });
      }
      });

    res.setHeader("content-type", "application/json");
    res.send(JSON.stringify({
      message: "Hi bb :>"
    }));
  });
});

app.listen(port, () => {
  // Connect to rabbitMQ
  console.log(`Listening on port ${port}`)
});