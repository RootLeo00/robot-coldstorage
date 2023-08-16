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
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxcoldstorageservice', graph_attr=nodeattr):
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          coldroom=Custom('coldroom','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
          led=Custom('led','./qakicons/symActorSmall.png')
          controllerled=Custom('controllerled','./qakicons/symActorSmall.png')
          guimok=Custom('guimok','./qakicons/symActorSmall.png')
     sys >> Edge(color='red', style='dashed', xlabel='pickupindoordone', fontcolor='red') >> coldstorageservice
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='howmanykgavailable', fontcolor='magenta') >> coldroom
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='dodepositaction', fontcolor='magenta') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatekg', fontcolor='blue') >> coldroom
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge( xlabel='robotstate', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge( xlabel='pickupindoordone', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> sonar
     sys >> Edge(color='red', style='dashed', xlabel='obstacle', fontcolor='red') >> sonar
     sonar >> Edge(color='blue', style='solid', xlabel='endalarm', fontcolor='blue') >> transporttrolley
     sonar >> Edge(color='blue', style='solid', xlabel='stopobstacle', fontcolor='blue') >> transporttrolley
     controllerled >> Edge(color='blue', style='solid', xlabel='ledCmd', fontcolor='blue') >> led
     sys >> Edge(color='red', style='dashed', xlabel='robotstate', fontcolor='red') >> controllerled
     guimok >> Edge(color='magenta', style='solid', xlabel='storefood', fontcolor='magenta') >> coldstorageservice
     guimok >> Edge(color='magenta', style='solid', xlabel='sendticket', fontcolor='magenta') >> coldstorageservice
diag
