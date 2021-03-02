package ie.wit.models;

interface TeamStore {
    fun findAll() : List<TeamModel>
    fun findById(id: Long) : TeamModel?
    fun create(team: TeamModel)
    //fun update(donation: DonationModel)
    //fun delete(donation: DonationModel)
}