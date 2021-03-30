// init datastore client
const Datastore = require('@google-cloud/datastore');
const datastore = Datastore();


/**
 * Background Cloud Function to be triggered by Pub/Sub.
 *
 * @param {object} data The Cloud Functions data.
 */
 exports.store = (data, context) => {

  // get data from event
  const pubSubMessage = data;
  // note: published text was x, received text is "x" (i.e. quotation marks are added)
  // text is base64 encoded, so decode first
  var string = pubSubMessage.data
  ? Buffer.from(pubSubMessage.data, 'base64').toString()
  : '';
  string = string.slice(1, string.length-1);
  console.log('string: ' + string);

  // create datastore key
  var key = datastore.key('Text');
  console.log('key: ' + key);
  //log('key', key);
  // create entity object
  // comprises the key and one property "Text" containing the text to store
  var entity = {
    key: key,
    data: {
      Text: string
    }
  };
  console.log('entity.key: ' + entity.key);
  console.log('entity.data: ' + entity.data);
  //log('entity', entity);
  //log('entity.data', entity.data);

  // save entity to datastore
  try {
    datastore.save(entity);
    console.log(`Task ${key.id} created successfully.`);
  } catch (err) {
    console.error('ERROR:', err);
  }
};


function log(name, obj) {
  console.log('log obj ' + name + '(' + obj + ')');
  Object.keys(obj).forEach(function (key) {
    var val = obj[key];
    // use val
    console.log('- ' + key + ' = ' + val);
  });
}