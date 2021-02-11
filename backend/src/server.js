'use strict';

const cors = require("cors");
const express = require("express");
const session = require('express-session')
const config = require("../config/myanimes.json");
const MongoStore = require('connect-mongo')(session);

const db = require("./database");

const app = express();

module.exports.start = () => new Promise((resolve, reject) => {

    app.set('trust proxy', 1);
    app.use(cors({ credentials: true, origin: config.cors }))
    app.use(express.json())
    app.use(session({
        secret: config.session.secret,
        resave: false,
        saveUninitialized: false,
        cookie: { secure: config.session.secure },
        store: new MongoStore({ mongooseConnection: db.getConnection() })
    }))

    app.listen(config.http.port, () => {
        resolve();
    });

});

module.exports.app = app;