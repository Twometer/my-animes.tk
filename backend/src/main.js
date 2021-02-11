'use strict';

const packageInfo = require("../package.json");
const config = require("../config/myanimes.json");

const xa = require("xa");

const db = require("./database");
const server = require("./server");

xa.info("Starting " + packageInfo.name + " v" + packageInfo.version + " on port " + config.http.port);

server.start().then(() => {
    xa.success("HTTP server online");
    db.connect(config.db.url);
});