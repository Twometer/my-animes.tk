import {Webapp, error} from "@twometer/webcore";
import * as kitsu from "../kitsu";

export default function (webapp: Webapp) {
    const route = webapp.route('/api/search')

    route.get('/', async (req, resp) => {
        let query = <string>req.query.q;
        if (!query || query.trim() == '')
            return error(resp, 400, 'Missing parameter q')

        let results = await kitsu.search(query);
        resp.json(results);
    })
}