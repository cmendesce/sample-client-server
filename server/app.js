const express = require('express');

const app = express();
app.use(require('morgan')('dev'));

const PORT = process.env.PORT || 3001;

app.get('/', async (req, res) => {
    try {
        res.status(200).send('Hello from the server!');
    } catch (error) {
        res.status(500).send(`Error calling server: ${error.message}`);
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});