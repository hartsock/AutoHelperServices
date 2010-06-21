package groovymag.jobs

class Job {
    Date dateCreated
    Date lastUpdated

    String message

    Boolean completed = false
    String errorMessage
    static constraints = {
      message(nullable:true)
      errorMessage(nullable:true)
    }
    static mapping = {
      message(type:'text')
      errorMessage(type:'text')
    }

    String toString() {
      def out = "'${message}'(completed:${completed})"
      if(errorMessage) out += "\nerrorMessage: ${errorMessage}"
      return out
    }

}
