import {Webapp} from "@twometer/webcore";
import config from "./config";
import express from "express";
import cors from "cors";
import session from "express-session";
import MongoStore from "connect-mongo";

const webapp = new Webapp(config.HTTP_PORT);

webapp.setup(app => {
    app.set('trust proxy', 1);
    app.use(express.json())
    app.use(cors({
        credentials: true,
        origin: config.CORS_ORIGIN
    }))
    app.use(session({
        secret: config.SESSION_SECRET,
        cookie: {secure: config.SESSION_SECURE},
        resave: false,
        saveUninitialized: false,
        store: MongoStore.create({mongoUrl: config.DB_URL})
    }))
})

export default webapp;