# superhero service

Please have a look at the API below.

This is the superhero service:
 * create a new superhero
 * get all superheros
 * get a specific superhero

## spring boot framework
 * spring boot 2.0.1.RELEASE
   - bundles all the necessary dependencies:
     + web
     + data
     + security
     + test
     + ...
   - easy to set up
   - great documentation and support

## The infrastructure
 * Superheros are stored within a mongodb collection.
 * Mongodb runs within a docker container.
 * The superhero service runs within a docker container.
 * AWS security policy only opens port `8080`.

## The superhero service
 * runs on port 8080.
 * expects the header `Content-Type: application/json`.
 * expects the appropriate header containing Basic Authentication information on every request.
   - There is no user management service, just a single in-memory user:
     + user: user
     + password: superSecretPasswd1

## A superhero
 * is stored within a mongodb collection.
 * has a unique name (names are case-insensitive).
   - An appropriate error message is returned when creating a superhero, whose name is taken already.
 * `firstAppearance` (`java.util.Date`) is returned in the format `YYYY-MM-DD`.

## The API
Please have a look at the directory [`test_curl/`](https://github.com/normoes/superheros/tree/master/test_curl)
 * `POST` `/superheros`
   - [create a new superhero](https://github.com/normoes/superheros/blob/master/test_curl/post.sh)
 * `GET` `/superheros`
   - [retrieve a list of all superheros](https://github.com/normoes/superheros/blob/master/test_curl/get_all.sh)
 * `GET` `/superheros/{name}`
   - [retrieve a specific superhero by his/her name](https://github.com/normoes/superheros/blob/master/test_curl/get_specific.sh)

## Further thoughts
There is no HTTPS support.

Eventually, I would recommend using nginx as reverse proxy, which then also handles 
 - HTTPS
 - authentication
 - user management

A reverse proxy has many other advantages
 - request limitation
 - domains
 - upstreams
 - port 80
 - ...

