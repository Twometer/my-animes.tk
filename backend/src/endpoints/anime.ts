import {Webapp, error} from "@twometer/webcore";
import * as kitsu from "../kitsu"

export default function (webapp: Webapp) {
    const route = webapp.route('/api/anime')

    route.get('/:animeId', async (req, resp) => {
        let anime = await kitsu.load(req.params.animeId);
        if (anime == null)
            error(resp, 404, `Unknown anime '${req.params.animeId}'`)
        else
            resp.json(anime);
    })
}