const express = require('express');
const axios = require('axios');

const app = express();
app.use(require('morgan')('dev'));
const PORT = 3000;

app.get('/', async (req, res) => {
    try {
        const response = await axios.get(`http://server:3000/`);
        res.status(200).send(response.data);
    } catch (error) {
        res.status(500).send(`Error calling server: ${error.message}`);
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});