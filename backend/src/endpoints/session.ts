import {Webapp, error} from "@twometer/webcore";

export default function (webapp: Webapp) {
    const route = webapp.route('/api/session')

    // Log in and create a new session
    route.post('/login', async (req, resp) => {

    });

    // Log out and destroy the current session
    route.post('/logout', async (req, resp) => {

    });
}