import mongoose from 'mongoose';
import config from './config'

export function connect() {
    return mongoose.connect(config.DB_URL, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    })
}