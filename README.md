# LetterRecognition

Recognition of Capital Latin letters in an input image by using its binary representation. The dataset used is based on UCI letter recognition dataset.
The UCI letter recognition dataset is created by representing images of letters as binary, and then using that information to classify each image base on 16 attributes. Those attributes are: 
1. x-box: the x position of the bounding box
2. y-box: the y position of the bounding box 
3. width of bounding box
4. height of bounding box
5. onpix: number of colored pixels, assuming image of letter has a white background (on pixels)
6. x-bar: mean x of on pixels in bounding box
7. y-bar: mean y of on pixels in bounding box
8. x2bar: mean x variance
9. y2bar: mean y variance	
10. xybar: mean x y correlation
11. x2ybr: mean of x * x * y
12. xy2br: mean of x * y * y
13. x-ege: mean edge count left to right - edge is a pixel immediately to the left of 0 or boundary
14. xegvy: correlation of x-ege with y
15. y-ege: mean edge count bottom to top - edge is a pixel immediately above of 0 or boundary
16. yegvx: correlation of y-ege with x
The value of the above attributes in the uci dataset was scaled to 0-15, making the recognition of a letter in a new image not possible. Hence, a new dataset was formed, with the values of the attributes left unscaled, since the smaller amount of images did not make the creation of a classification method slow.

The api used to create and train the model for the recognition is encog.

UCI Letter Recognition Dataset: https://archive.ics.uci.edu/ml/datasets/letter+recognition

Encog : https://www.heatonresearch.com/encog/
