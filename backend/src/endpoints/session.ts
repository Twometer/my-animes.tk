import * as db from "../database"
import {Webapp, error, Request} from "@twometer/webcore";
import {hash} from "../hash";

declare module 'express-session' {
    interface SessionData {
        userId: String;
        username: String;
        loggedIn: boolean;
    }
}

const LoginRequest = new Request({
    username: String,
    password: String
})

export default function (webapp: Webapp) {
    const route = webapp.route('/api/session')

    // Log in and create a new session
    route.post('/login', async (req, resp) => {
        if (req.session.loggedIn)
            return error(resp, 400, 'Already logged in');

        let body = LoginRequest.parse(req.body, resp);
        if (body == null) return;

        let user = await db.User.findOne({username: body.username});
        if (user == null)
            return error(resp, 401, 'Invalid credentials');

        let hashedInput = await hash(body.password, body.username);
        if (user.password != hashedInput)
            return error(resp, 401, 'Invalid credentials');

        req.session.userId = user._id;
        req.session.username = user.username;
        req.session.loggedIn = true;

        return resp.status(204).send();
    });

    // Log out and destroy the current session
    route.post('/logout', async (req, resp) => {
        if (!req.session.loggedIn)
            return error(resp, 401);

        req.session.userId = '';
        req.session.username = '';
        req.session.loggedIn = false;

        return resp.status(204).send();
    });
}