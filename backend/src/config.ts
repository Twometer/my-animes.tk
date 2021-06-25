import {Config} from "@twometer/webcore";

export default Config.from({
    HTTP_PORT: 8090,
    CORS_ORIGIN: "http://localhost:8080",
    SESSION_SECURE: false,
    SESSION_SECRET: "secret",
    ANIME_TTL: 14,
    DB_URL: "mongodb://localhost:27017/myanimes",
    REDIS_URL: "redis://localhost/"
}).read()