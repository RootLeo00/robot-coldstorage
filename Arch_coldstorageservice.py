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
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='howmanykgavailable', fontcolor='magenta') >> coldroom
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
diag
