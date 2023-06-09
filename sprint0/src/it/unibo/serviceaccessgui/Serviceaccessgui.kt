/* Generated by AN DISI Unibo */ 
package it.unibo.serviceaccessgui

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Serviceaccessgui ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitticketrequest", cond=doswitch() )
				}	 
				state("waitticketrequest") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("handleticketrequest") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						request("storefood", "storefood($Mass)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t19",targetState="ticketaccepted",cond=whenReply("ticketaccepted"))
					transition(edgeName="t110",targetState="ticketdenied",cond=whenReply("ticketdenied"))
				}	 
				state("ticketaccepted") { //this:State
					action { //it:State
						 CommUtils.outgreen("ticket")  
						if( checkMsgContent( Term.createTerm("ticketaccepted(TICKETCODE)"), Term.createTerm("acceptticket(TICKETCODE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
												val TicketCode= payloadArg(0)
												CommUtils.outmagenta("ticket: ${TicketCode}"); 
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("handleticketfromuser") { //this:State
					action { //it:State
						request("sendticket", "sendticket($Ticket)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitticketrequest", cond=doswitch() )
				}	 
				state("ticketdenied") { //this:State
					action { //it:State
						 CommUtils.outred("not enough space")  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitticketrequest", cond=doswitch() )
				}	 
			}
		}
}
