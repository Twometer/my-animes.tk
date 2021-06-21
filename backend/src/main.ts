import log from 'cutelog.js'
import config from '../config/myanimes.json'
import manifest from '../package.json'
import {Webapp} from "@twometer/webcore";
import cors from 'cors'
import express from 'express'
import session from 'express-session'
import MongoStore from "connect-mongo";

log.info(`Starting ${manifest.name} v${manifest.version} on port ${config.http.port}`);

let webapp = new Webapp(config.http.port);

webapp.setup(app => {
    app.set('trust proxy', 1);
    app.use(express.json())
    app.use(cors({
        credentials: true,
        origin: config.http.cors
    }))
    app.use(session({
        secret: config.session.secret,
        cookie: {secure: config.session.secure},
        resave: false,
        saveUninitialized: false,
        store: MongoStore.create({mongoUrl: config.db.url})
    }))
})

webapp.start().then(() => {
    log.okay('Startup complete')
})