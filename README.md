This applications reads in a pdf stored in the resources on start up and uses Tokenisation to store embeddings in a redis vector database. This is deleted when the application stops

An Api is exposed which takes a question from a user and does a similarity search to get the relevant vectors from the redis database.
Once the data is recieved the response is wrapped it in a pre defined prompt and sets the context as data recieved from the vector database.
The prompt is then sent to open api to get a context aware response

To run this application you will need to pass in the following variables environment variables:

- AI_API_KEY - API KEY FROM https://platform.openai.com/
- REDIS_PORT - Connection details from https://app.redislabs.com/#/
- REDIS_HOST - Connection details from https://app.redislabs.com/#/
- REDIS_PASSWORD - Connection details from https://app.redislabs.com/#/
- REDIS_USERNAME - Connection details from https://app.redislabs.com/#/

  You will need to obtain an api key from  https://platform.openai.com/
  You will need to create a free Redis db using https://app.redislabs.com/#/
