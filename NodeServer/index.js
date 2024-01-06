const express = require('express')
const app = express()
const port = 3000

app.get('/news', (req, res) => {
    const newsItems = [
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "Fusce a quam at magna dapibus finibus elementum a mi.",
        "Nunc ullamcorper arcu sed lectus ornare cursus.",
        "Suspendisse potenti. Aliquam erat volutpat.",
        "Sed ut odio ut ipsum lobortis maximus.",
        "Sed id tellus vitae elit ultricies lacinia.",
    ]
    const item = [{
        news: newsItems[Math.floor(Math.random() * newsItems.length)],
    }]
    res.send(item)
})

app.get('/:leeftijd', (req, res) => {
    const randomStatement = [
        "Vanaf nu kan je baby licht en geluid waarnemen.",
        "De baby kan nu zijn/haar hoofdje bewegen.",
        "Vanaf nu begint de baby te gapen.",
    ]
    res.send(`Jou baby is nu ${req.params.leeftijd} weken oud! ${randomStatement[Math.floor(Math.random() * randomStatement.length)]}`)
})

app.listen(port, () => {
  console.log(`Mock server is running on port ${port}`)
})