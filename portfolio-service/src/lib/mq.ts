import * as amqp from "amqplib";

export const URL = "amqp://seiji:foobar@localhost:5672"
export let connection: amqp.Connection | undefined;

export async function amqpConnect(url: string): Promise<amqp.Connection> {
  if (!connection) {
    connection = await amqp.connect(url)
  }
  return connection
}

export async function amqpDisconnect() {
  await connection?.close();
  connection = undefined;
}