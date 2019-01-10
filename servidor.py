import Pyro4

@Pyro4.expose
class Saludo:
    def saludar(self):
        return 'Hola'
#se puede recibir un host,puerto
daemon = Pyro4.Daemon()
uri = daemon.register(Saludo)        
print(uri)
daemon.requestLoop()