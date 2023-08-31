from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('appl1qakArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxall', graph_attr=nodeattr):
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          coldroom=Custom('coldroom','./qakicons/symActorSmall.png')
          coldstoragerobot=Custom('coldstoragerobot','./qakicons/symActorSmall.png')
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
          fakeuser=Custom('fakeuser','./qakicons/symActorSmall.png')
     with Cluster('ctxrobotpos', graph_attr=nodeattr):
          robotpos=Custom('robotpos(ext)','./qakicons/externalQActor.png')
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='getcoldroomspace', fontcolor='magenta') >> coldroom
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='startrobotservice', fontcolor='blue') >> coldstoragerobot
     sys >> Edge(color='red', style='dashed', xlabel='robotincoldstorage', fontcolor='red') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatestorage', fontcolor='blue') >> coldroom
     coldstoragerobot >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> robotpos
     coldstoragerobot >> Edge( xlabel='robotisindoor', **eventedgeattr, fontcolor='red') >> sys
     coldstoragerobot >> Edge( xlabel='robotisinstorage', **eventedgeattr, fontcolor='red') >> sys
     coldstoragerobot >> Edge( xlabel='robotisinhome', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='guicmd', fontcolor='red') >> serviceaccessgui
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='createticket', fontcolor='magenta') >> coldstorageservice
     serviceaccessgui >> Edge( xlabel='ticketaccepted', **eventedgeattr, fontcolor='red') >> sys
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='sendcamion', fontcolor='magenta') >> coldstorageservice
     fakeuser >> Edge( xlabel='guicmd', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='ticketaccepted', fontcolor='red') >> fakeuser
diag
