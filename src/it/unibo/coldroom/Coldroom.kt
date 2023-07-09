/* Generated by AN DISI Unibo */ 
package it.unibo.coldroom

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Coldroom ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 var Kgstored : Long = 100  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("&&&  appl coldroom is now ACTIVE ...")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t04",targetState="replywithcoldroomspacestate",cond=whenRequest("howmanykgavailable"))
					transition(edgeName="t05",targetState="handleupdateKg",cond=whenDispatch("updatekg"))
				}	 
				state("replywithcoldroomspacestate") { //this:State
					action { //it:State
						answer("howmanykgavailable", "kgavailable", "kgavailable($Kgstored)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t06",targetState="replywithcoldroomspacestate",cond=whenRequest("howmanykgavailable"))
					transition(edgeName="t07",targetState="handleupdateKg",cond=whenDispatch("updatekg"))
				}	 
				state("handleupdateKg") { //this:State
					action { //it:State
						 
									var insertkg = payloadArg(0).toLong();
									Kgstored += insertkg;
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
