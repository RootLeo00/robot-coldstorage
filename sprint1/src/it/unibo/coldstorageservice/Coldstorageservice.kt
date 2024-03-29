/* Generated by AN DISI Unibo */ 
package it.unibo.coldstorageservice

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Coldstorageservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
					var Kgtoload : Int = 0; 
					var Expirationtime:Long = DomainSystemConfig.getExpirationTime();
					var ticketList=it.unibo.ticket.TicketList(Expirationtime);
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outgreen("$name | wait for messages")
						discardMessages = false
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="askhowmanykgoccupied",cond=whenRequest("storefood"))
					transition(edgeName="t01",targetState="checkforticketexpired",cond=whenRequest("sendticket"))
					transition(edgeName="t02",targetState="sendchargetaken",cond=whenDispatch("pickupindoordone"))
					transition(edgeName="t03",targetState="updatecoldstorage",cond=whenReply("depositactionended"))
				}	 
				state("askhowmanykgoccupied") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("storefood(KG)"), Term.createTerm("storefood(KG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Kgtoload = payloadArg(0).toInt();  
								CommUtils.outgreen("$name | chilograms to load $Kgtoload ")
								request("howmanykgavailable", "howmanykgavailable" ,"coldroom" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t04",targetState="checkifthereisenoughspace",cond=whenReply("kgavailable"))
				}	 
				state("checkifthereisenoughspace") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("kgavailable(KG)"), Term.createTerm("kgavailable(KG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Kgavailable = payloadArg(0).toLong(); 
												var Totalkgtostore=ticketList.getTotalKgToStore();
								CommUtils.outgreen("$name | total kg of current tickets: $Totalkgtostore")
								if(  Kgtoload <= Kgavailable-Totalkgtostore 
								 ){ 
												var ticket=ticketList.createTicket(Kgtoload);
											   var TICKETCODE = ticket.getTicketNumber();
											   var TICKETSECRET= ticket.getTicketSecret();
											   var TIMESTAMP = ticket.getTimestamp(); 
								CommUtils.outgreen("$name | ticket accepted with $TICKETCODE, $TICKETSECRET, $TIMESTAMP")
								answer("storefood", "ticketaccepted", "ticketaccepted($TICKETCODE,$TICKETSECRET,$TIMESTAMP)"   )  
								}
								else
								 {CommUtils.outgreen("$name | ticket denied")
								 answer("storefood", "ticketdenied", "ticketdenied(ARG)"   )  
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("checkforticketexpired") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("sendticket(TICKETCODE,TICKETSECRET)"), Term.createTerm("sendticket(TICKETCODE,TICKETSECRET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								var TICKETCODE = payloadArg(0).toInt();
											   var TICKETSECRET= payloadArg(1);
											   var Ticket=ticketList.getTicket(TICKETCODE);
								if( ticketList.isExpired(Ticket)  
								 ){answer("sendticket", "ticketexpired", "ticketexpired(ARG)"   )  
								}
								else
								 {if( !Ticket.getTicketSecret().equals(TICKETSECRET)  
								  ){answer("sendticket", "ticketrejected", "ticketrejected(ARG)"   )  
								 }
								 else
								  {Ticket.setStatus(1); 
								  request("dodepositaction", "dodepositaction($TICKETCODE)" ,"transporttrolley" )  
								  }
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("sendchargetaken") { //this:State
					action { //it:State
						answer("sendticket", "chargetaken", "chargetaken(ok)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("updatecoldstorage") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("depositactionended(ARG)"), Term.createTerm("depositactionended(TICKETCODE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Ticketcode=payloadArg(0).toInt();
								CommUtils.outgreen("$name | updating coldroom status ending operation number: $Ticketcode")
								
									  				var Ticket=ticketList.getTicket(Ticketcode);
									  				var Kg=Ticket.getKgToStore();
									  				var L=ticketList.toString();
								forward("updatekg", "updatekg($Kg)" ,"coldroom" ) 
								CommUtils.outgreen("$L")
								
									  			ticketList.removeTicket(Ticketcode);
									  			L=ticketList.toString(); 
								CommUtils.outgreen("$L")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
