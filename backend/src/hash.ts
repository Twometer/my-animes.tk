import crypto from 'crypto'

export function hash(key: string, salt: string): Promise<string> {
    return new Promise((resolve, reject) => {
        crypto.pbkdf2(key, salt, 100000, 48, 'sha512', (err, key) => {
            if (err)
                reject(err);
            else
                resolve(key.toString("base64"));
        })
    });
}