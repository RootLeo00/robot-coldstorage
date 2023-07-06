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
with Diagram('coldstorageserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxserviceaccessgui', graph_attr=nodeattr):
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
     with Cluster('ctxcoldstorageservice', graph_attr=nodeattr):
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          coldroom=Custom('coldroom','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
     with Cluster('ctxsonar', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='getcoldroomspace', fontcolor='magenta') >> coldroom
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> serviceaccessgui
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='startrobotservice', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatestorage', fontcolor='blue') >> coldroom
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='createticket', fontcolor='magenta') >> coldstorageservice
     serviceaccessgui >> Edge(color='blue', style='solid', xlabel='sendtruck', fontcolor='blue') >> coldstorageservice
     sonar >> Edge(color='blue', style='solid', xlabel='stoprobot', fontcolor='blue') >> transporttrolley
diag
