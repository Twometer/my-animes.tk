import {Webapp, error} from "@twometer/webcore";

export default function (webapp: Webapp) {
    const route = webapp.route('/api/user')

    // Create a new user
    route.post('/new', async (req, resp) => {

    })

    // Delete a user account [Requires auth]
    route.delete('/:username', async (req, resp) => {

    })

    // Get the profile for the given username
    route.get('/:username/profile', async (req, resp) => {

    })

    // Update the profile for the given username [Requires auth]
    route.put('/:username/profile', async (req, resp) => {

    });

    // Change the password for the given username [Requires auth]
    route.put('/:username/password', async (req, resp) => {

    });

    // Get the watchlist for the given username
    route.get('/:username/list', async (req, resp) => {

    });

    // Add a new anime to the watchlist [Requires auth]
    route.post('/:username/list/new', async (req, resp) => {

    });

    // Update the data for one item on the watchlist [Requires auth]
    route.put('/:username/list/:itemId', async (req, resp) => {

    });

    // Remove an item from the watchlist [Requires auth]
    route.delete('/:username/list/:itemId', async (req, resp) => {

    });
}