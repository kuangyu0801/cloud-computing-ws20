// init Translate client
const Translate = require('@google-cloud/translate');
const translateClient = Translate();

// init pub-sub client
const PubSub = require('@google-cloud/pubsub');
const pubsub = PubSub();
const topicStorage = pubsub.topic('text-storage');


/**
 * Background Cloud Function to be triggered by Pub/Sub.
 *
 * @param {object} data The Cloud Functions data.
 */
 exports.translate = (data, context) => {
  // get data from event
  const pubSubMessage = data;
  // note: published text was x, received text is "x" (i.e. quotation marks are added)
  // text is base64 encoded, so decode first
  const string = pubSubMessage.data
  ? Buffer.from(pubSubMessage.data, 'base64').toString()
  : '';
  const jsondata = JSON.parse(string);
  console.log('jsondata: ' + jsondata);

  // create options to be sent to Translate API
  var options = {
    from: jsondata.sourceLang,
    to: jsondata.targetLang
  };
  // translate text
  translateClient.translate(jsondata.text, options)
  .then((results) => {
    const translation = results[0];
    console.log(`Text: ${jsondata.text}`);
    console.log(`Translation: ${translation}`);
		// send text to storage topic
		console.log('Sending text to "text-storage" topic (' + translation + ').');
		topicStorage.publish(translation);
  })
  .catch((err) => {
    console.error('ERROR:', err);
  });
};