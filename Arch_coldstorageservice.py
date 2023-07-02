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
     with Cluster('ctxall', graph_attr=nodeattr):
          fakeuser=Custom('fakeuser','./qakicons/symActorSmall.png')
     with Cluster('ctxtransporttrolley', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
     with Cluster('ctxserviceaccessgui', graph_attr=nodeattr):
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
     with Cluster('ctxcoldstorageservice', graph_attr=nodeattr):
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
     with Cluster('ctxsonar', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
     with Cluster('ctxcoldroom', graph_attr=nodeattr):
          coldroom=Custom('coldroom','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='getcoldroomspace', fontcolor='magenta') >> coldroom
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='startrobotservice', fontcolor='blue') >> transporttrolley
     sys >> Edge(color='red', style='dashed', xlabel='robotincoldstorage', fontcolor='red') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatestorage', fontcolor='blue') >> coldroom
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge( xlabel='robotisindoor', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( xlabel='robotisinstorage', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( xlabel='robotisinhome', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='guicmd', fontcolor='red') >> serviceaccessgui
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='createticket', fontcolor='magenta') >> coldstorageservice
     serviceaccessgui >> Edge( xlabel='ticketaccepted', **eventedgeattr, fontcolor='red') >> sys
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='sendcamion', fontcolor='magenta') >> coldstorageservice
     fakeuser >> Edge( xlabel='guicmd', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='ticketaccepted', fontcolor='red') >> fakeuser
diag
