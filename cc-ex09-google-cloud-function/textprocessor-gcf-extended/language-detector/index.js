// init translate client
const Translate = require('@google-cloud/translate');
const translateClient = Translate();

// init pub-sub client
const PubSub = require('@google-cloud/pubsub');
const pubsub = PubSub();
const topicStorage = pubsub.topic('text-storage');
const topicTranslation = pubsub.topic('text-translation');

/**
 * Background Cloud Function to be triggered by Pub/Sub.
 *
 * @param {object} event The Cloud Functions event.
 */
 exports.detectLanguage = (data, context) => {
  // get data from event
  const pubSubMessage = data;
  // note: published text was x, received text is "x" (i.e. quotation marks are added)
  // text is base64 encoded, so decode first
  var text = pubSubMessage.data
  ? Buffer.from(pubSubMessage.data, 'base64').toString()
  : '';
  text = text.slice(1, text.length-1);
  // remove quotation marks, are always added by pub-sub (?)


  console.log('text, base64: ' + pubSubMessage.data);
  console.log('text, decoded: ' + text);

  // Detects the language. "text" can be a string for detecting the language of
  // a single piece of text, or an array of strings for detecting the languages
  // of multiple texts.
  translateClient.detect(text)
  .then((results) => {
    let detections = results[0];
    // we expect only one detection, but to be future proof...
    detections = Array.isArray(detections) ? detections : [detections];
    console.log('Detections:');
    detections.forEach((detection) => {
      console.log(`${detection.input} => ${detection.language}`);
      console.log('Language detection "' +detection.language + '" with confidence ' + detection.confidence + ' (' + detection.input +').');
    // english? store!
    if (detection.language == 'en') {
      // send text to storage topic
      console.log('Sending text to "text-storage" topic (' + detection.input + ').');
      topicStorage.publish(detection.input);
    } else {
      // construct translation request, then send to translation topic
      // we want everything to be translated into english
      var msg = {
        sourceLang : detection.language,
        targetLang : 'en',
        text : detection.input
      };
      // send to translation topic
      console.log('Sending msg to "text-translation" topic (' + msg + ').');
      topicTranslation.publish(msg);
    }
  });
  })
  .catch((err) => {
    console.error('ERROR:', err);
  });
};