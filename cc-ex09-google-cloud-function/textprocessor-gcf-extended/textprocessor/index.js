// init pub-sub client
const PubSub = require('@google-cloud/pubsub');
const pubsub = PubSub();
const topic = pubsub.topic('language-detection');

/**
 * Simple Text Processor as HTTP Cloud Function.
 *
 * @param {Object} req Cloud Function request context.
 * @param {Object} res Cloud Function response context.
 */
exports.processText = function processText (req, res) {
  // forward text to pub-sub
  console.log('publish to language-detection: ' + req.body);
  topic.publish(req.body);
  // return processed text
  console.log('processing: ' + req.body);
  res.send('[processed by gcf] - ' + req.body);
};