### Simple HTTP server for storing and serving arbitrary files in the local filesystem

## Build & run

To build application you need to go to the project directory and provide command in terminal:

`./gradlew clean build` (Linux) or

`gradlew.bat clean build` (Windows)

To run application you need to run command:

`java -jar build/libs/filemanager-1.0-SNAPSHOT.jar`

# With docker

To build image:

`docker build -t filemanager .`

To run container:

`docker run -dp 8080:8080 --rm -v {local_directory_path}:/uploads --name filemanager filemanager`

where `{local_directory_path}` - path to uploads directory in local filesystem which be mounted to the docker container


## Available endpoints

1. POST /files/

Add attachment to the form-data params with name `file`

Response example:

/* example response */

    {  

        "ref": "b3175398-12bb-4cd0-b372-45eba819f37b",  

        "content_type": "image/png",
 
        "size_bytes": 93840
 
    }

2. GET /files/content/{ref}

    /* 200 with the file content in the respnse body */
    
3. DELETE /files/content/{ref}

    /* 204 empty response */
