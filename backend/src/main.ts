import log from 'cutelog.js'
import manifest from '../package.json'
import config from "./config";
import webapp from "./rest";
import * as database from './database'

log.info(`Starting ${manifest.name} v${manifest.version} on port ${config.HTTP_PORT} ...`);

database.connect().then(() => {
    log.okay('Database connection established')
})

webapp.start().then(() => {
    log.okay('REST server started')
})
