import {Webapp} from "@twometer/webcore";
import * as kitsu from "../kitsu";

export default function (webapp: Webapp) {
    const route = webapp.route('/api/search')

    route.get('/:query', async (req, resp) => {
        let results = await kitsu.search(req.params.query);
        resp.json(results);
    })
}