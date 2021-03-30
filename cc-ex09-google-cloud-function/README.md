# EX09

## Task 3
-reference to EX-04

```
wget https://github.com/CC-Exercises/notebookapp/releases/download/v0.4.0/notebookapp-0.4.0.jar 
wget https://github.com/CC-Exercises/notebookapp/releases/download/v0.4.0/config.yml
java -jar notebookapp-0.4.0.jar server config-min.yml
```

- need to select "allowed unauthenticated invocation" in google cloud function for HTTP trigger

## Task 4
- most of the @google-cloud/* are deprecated, stick to the one provided and it works just fine

- need to enable @google-cloud/translate API, 
