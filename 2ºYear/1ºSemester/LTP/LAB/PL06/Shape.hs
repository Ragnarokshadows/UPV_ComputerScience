type Height = Float
type Width  = Float
type Radius = Float
data Rectangle = Rectangle Height Width 
data Circle = Circle Radius

class (Eq a, Show a) => Shape a where
   area :: a -> Float
   perimeter :: a -> Float
   surfacePrism ::  a -> Height -> Float

instance Shape Rectangle where
   area (Rectangle h w) = h * w
   perimeter (Rectangle h w) = 2 * (h + w)
   surfacePrism (Rectangle h w) x = (2 * h * w) + (2 * h * x) + (2 * w * x)

instance Shape Circle where
   area (Circle r) = pi * r**2
   perimeter (Circle r) = 2 * pi * r
   surfacePrism (Circle r) x = (2 * pi * r**2) + (x * 2 * pi * r)

instance Eq Rectangle where
   (Rectangle h w) == (Rectangle g i) = (h==g && w==i)

instance Eq Circle where
   (Circle r) == (Circle h) = (r==h)

instance Show Rectangle where
   show (Rectangle h w) = "Rectangle: height = " ++ (show h) ++ 
                          ", width = " ++ (show w)

instance Show Circle where 
   show (Circle r) = "Circle: radius = " ++ (show r)
