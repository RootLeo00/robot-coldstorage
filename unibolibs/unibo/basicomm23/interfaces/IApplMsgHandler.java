package unibo.basicomm23.interfaces;

public interface IApplMsgHandler  {
	public String getName(); 
	public void elaborate( IApplMessage message, Interaction2021 conn );//ESTENSIONE dopo Context
	public void sendMsgToClient( String message, Interaction2021 conn );
	public void sendAnswerToClient( String message, Interaction2021 conn  );
}
