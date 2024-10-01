<a id="readme-top"></a>
# Project: Login API
> Sunnickel | 01.10.2024
---

[![Stargazers][stars-shield]][stars-url]
[![MIT License][license-shield]][license-url]

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
  </ol>
</details>

## About The Project
This is a small api, which can be used for as an example a little game with multiplayer or maybe something else. It's there to be an authentication bridge
between a client and a server without exposing passwords or other sensitive data to each other and without anyone being able to just log into your account or read out packets.
I'm no cybersecurity expert so it will probably have some flaws.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With
* MariaDB
* Spring Boot
* BCrypt

## Getting Started
### Prerequisites
- install a mariadb server on the device of your API or a device you can reach from your API device
- if you installed it on another device allow other devices to access the database
- create a user in mariadb to access the database
  ```mysql
  CREATE USER 'username'@'host' IDENTIFIED BY 'password';
  ```
    - 'host' is the ip from which you're gonna access it
        - if you installed it on the same device put 'localhost'
        - if you installed it on another device put its ip address
        - if you want to access it from everywhere put '%'
- Create a database
  ```mysql
  CREATE DATABASE name IF NOT EXISTS
  ```
- Grant Permissions for your User to the database
  ```mysql
  GRANT ALL PRIVILEGES ON *.* TO 'username'@'host';
  ```
> [!IMPORTANT]
> You're granting this user all permissions on every database in mariadb. If you don't want this read yourself into mariadb.

### Installation
1. Download the Zip of the project [here](https://github.com/Sunnickel/LoginAPI/archive/refs/heads/master.zip)
2. Unzip it and open the application.proerties in /src/main/resources
3. Change this lines to the information we made before
   *I know my infos are still there ignore them, you won't get far with them*
   ```propertiers
   spring.datasource.url=jdbc:mariadb://host:3306/databasename
   spring.datasource.username=username
   spring.datasource.password=password
   ```
4. Congrats you now have a login API in Java
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage
The Api works that way that both all Users and the Server
### /register
Registers the Client in the API and returns a hashed password, which will be used to log in
- Post by Client
  ```json
  {
    "id": 0,
    "name": "name",
    "password": "password"
  }
  ```
- returns
  ```json
  {
    "id": 0,
    "token": "Hashed Password"
  }
  ```

### /login 
logs the user in and returns a one time token which can be sent to the e.g. game server to authenticate yourself there
- Post by Client
  ```json
  {
    "id": 0,
    "password": "Hashed Password"
  }
  ```
- returns
  ```json
  {
    "id": 0,
    "ottoken": "One Time Token"
  }
  ```

### /server/register 
registers the server (only one possible, thats hardcoded) and returns a token which will be used to verify a client
- Post by Server
  ```json
  {
    "password": "password"
  }
  ```
- returns
  ```json
  {
    "token": "Verify Token"
  }
  ```

### /verify 
Asks the API with a verify token what the one time token of the client is to compare it if the user wants to log in (the one time token will be removed as soon as you verify it once and the client will need to log in again to get a new one time token.
- Post by Server
  ```json
  {
    "id": 0,
    "token": "Verify Token"
  }
  ```
- returns
  ```json
  {
    "id": 0,
    "ottoken": "Client One Time Token"
  }
  ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Roadmap
- [ ] reformat code
- [ ] make code faster
- [ ] fix security (?)
<p align="right">(<a href="#readme-top">back to top</a>)</p>



[stars-shield]: https://img.shields.io/github/stars/sunnickel/LoginAPI.svg?style=for-the-badge
[stars-url]: https://github.com/sunnickel/LoginAPI/stargazers

[license-shield]: https://img.shields.io/github/license/sunnickel/LoginAPI.svg?style=for-the-badge
[license-url]: https://github.com/sunnickel/LoginAPI/blob/master/LICENSE.txt
