const fs = require('fs');

fs.readdir(process.cwd(), (err, files) => {

    if (err) throw err;

    files.filter(f => f.endsWith('.css')).forEach(file => {
        console.log()
        fs.readFile(file, 'utf8', (err, data) => {

            if (err) throw err;

            const result = data.replace('@charset "UTF-8";', '');

            fs.writeFile(file, result, 'utf8', (err) => {
                if (err) throw err;
            });

        });

    });

});