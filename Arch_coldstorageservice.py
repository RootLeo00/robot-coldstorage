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
          sonarfisico=Custom('sonarfisico(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
     sys >> Edge(color='red', style='dashed', xlabel='robotisinindoor', fontcolor='red') >> coldstorageservice
     sys >> Edge(color='red', style='dashed', xlabel='depositactionended', fontcolor='red') >> coldstorageservice
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='howmanykgavailable', fontcolor='magenta') >> coldroom
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='dodepositaction', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatekg', fontcolor='blue') >> coldroom
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge( xlabel='robotismoving', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='stopobstacle', fontcolor='red') >> transporttrolley
     transporttrolley >> Edge( xlabel='robotisinindoor', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( xlabel='depositactionended', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( xlabel='robotisinhome', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='endalarm', fontcolor='red') >> transporttrolley
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> sonar
     sys >> Edge(color='red', style='dashed', xlabel='obstacle', fontcolor='red') >> sonar
     sonar >> Edge( xlabel='endalarm', **eventedgeattr, fontcolor='red') >> sys
     sonar >> Edge( xlabel='stopobstacle', **eventedgeattr, fontcolor='red') >> sys
     controllerled >> Edge(color='blue', style='solid', xlabel='ledCmd', fontcolor='blue') >> led
     sys >> Edge(color='red', style='dashed', xlabel='robotismoving', fontcolor='red') >> controllerled
     sys >> Edge(color='red', style='dashed', xlabel='robotisinhome', fontcolor='red') >> controllerled
     sys >> Edge(color='red', style='dashed', xlabel='robotisstopped', fontcolor='red') >> controllerled
     guimok >> Edge(color='magenta', style='solid', xlabel='storefood', fontcolor='magenta') >> coldstorageservice
     guimok >> Edge(color='magenta', style='solid', xlabel='sendticket', fontcolor='magenta') >> coldstorageservice
diag
