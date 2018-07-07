# Distributed-computing-prime-numbers

Java app using TCP/IP protocol to compute prime numbers on multiple computers on LAN.

## Description

Distributed computing app which computes prime numbers in given range on multiple computers on LAN using TCP/IP protocol. 

This is a client / server app.

How it works?

1. We need to start server by specifying console arguments 'server' and 'server port'
2. We need to specify range in which we want to search prime numbers
3. Now server is active and is waiting for clients to connect. 
4. We can connect to server by specifying console arguments 'client' 'server ip' 'port'
5. When appropriate number of clients connects to server, server divides the work by all connected clients and send tasks to each client
6. When client finish his task, he sends the result back to server. If client disconnects during computations, server sends his task to another not busy client
8. When all client finish their work and every part is computed, server displays the result.

## More info

Project made for [Codecool](https://codecool.com/) programming course.
