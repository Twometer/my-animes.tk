'use strict';

const xa = require("xa");
const mongoose = require("mongoose");

module.exports.connect = (url) => {
    mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true })
        .then(() => xa.success("Connected to database"));
}

module.exports.getConnection = () => mongoose.connection;