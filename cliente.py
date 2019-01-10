import Pyro4

o = Pyro4.Proxy("PYRO:obj_cde7fdf87c8f4dc6be7f86f64b8d4579@localhost:51043")

print(o.saludar())