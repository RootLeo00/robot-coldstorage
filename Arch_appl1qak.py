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
          coldstorage=Custom('coldstorage','./qakicons/symActorSmall.png')
          coldstoragerobot=Custom('coldstoragerobot','./qakicons/symActorSmall.png')
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
     with Cluster('ctxrobotpos', graph_attr=nodeattr):
          worker=Custom('worker(ext)','./qakicons/externalQActor.png')
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='checkspace', fontcolor='magenta') >> coldstorage
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='startrobotservice', fontcolor='blue') >> coldstoragerobot
     sys >> Edge(color='red', style='dashed', xlabel='robotincoldstorage', fontcolor='red') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatestorage', fontcolor='blue') >> coldstorage
     coldstoragerobot >> Edge( xlabel='robotisindoor', **eventedgeattr, fontcolor='red') >> sys
     coldstoragerobot >> Edge( xlabel='robotisinstorage', **eventedgeattr, fontcolor='red') >> sys
     coldstoragerobot >> Edge( xlabel='robotisinhome', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='guicmd', fontcolor='red') >> serviceaccessgui
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='createticket', fontcolor='magenta') >> coldstorageservice
     serviceaccessgui >> Edge(color='blue', style='solid', xlabel='camionin', fontcolor='blue') >> coldstorageservice
diag
