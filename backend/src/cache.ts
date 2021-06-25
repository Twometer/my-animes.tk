import redis from 'redis'
import config from './config'
import * as log from 'cutelog.js'
import {promisify} from "util";

const client = redis.createClient({url: config.REDIS_URL});
client.on('error', error => {
    log.error(`Redis error: ${error}`)
})
client.on('ready', () => {
    log.okay('Redis cache ready')
})

const get = promisify(client.get).bind(client)
const set = promisify(client.set).bind(client)

const State = {
    // Has a valid entry in the database
    Present: 'state::present',

    // Has no valid entry in the database, and does not exist upstream
    Nonexistent: 'state::nonexistent',

    // Has never been fetched, and state is unknown
    NeverCached: null
}

export {get, set, State}
