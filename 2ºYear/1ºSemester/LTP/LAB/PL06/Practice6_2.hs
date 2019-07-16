type Height = Float
type Width  = Float
type Radius = Float
data Rectangle = Rectangle Height Width 
data Circle = Circle Radius

class Shape a where
   area :: a -> Float
   perimeter :: a -> Float

instance Shape Rectangle where
   area (Rectangle h w) = h * w
   perimeter (Rectangle h w) = 2 * (h + w)

instance Shape Circle where
   area (Circle r) = pi * r**2
   perimeter (Circle r) = 2 * pi * r

