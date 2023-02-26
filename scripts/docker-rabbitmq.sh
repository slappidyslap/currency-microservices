docker run \
  -d --rm \
  --hostname rabbitmq \
  --name rmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management-alpine