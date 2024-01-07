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
    },{
        news: newsItems[Math.floor(Math.random() * newsItems.length)],
    }]
    res.json(item)
})

app.listen(port, () => {
  console.log(`Mock server is running on port ${port}`)
})