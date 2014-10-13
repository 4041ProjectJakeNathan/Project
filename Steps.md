1) Process image into an array
2) RGB to grayscale. Create pixel object, pixel[][] array. Pixel has (r,g,b) or grayscale for color options. Boolean switches
3) Filtering- set a minimum size for how many pixels need to define an edge, or that noise doesn't close on itself
4) Edge Extraction- calculus, layered arrays based on tolerance
5) Edge thinning- algorithm in Nikos' notes
6) Line fitting- least squared regression
7) Display- How to turn array of rgb values into a .jpg - Maybe a higher priority- tkinter
8) Report- 5 page report

Ideas:
1) Python flood fill- links in resources
2) Starting with grayscale and using floodfill to flip pixel representation from rgb to grayscale
  (115, 230, 85) - (230, 230, 230) flipping boolean
3) Effeciency- start brute force, find ways to not go over same data- flip noise to null- multiple arrays             lines/start/final







