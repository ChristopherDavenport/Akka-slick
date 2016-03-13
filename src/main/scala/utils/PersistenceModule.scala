package utils

import akka.actor.{ActorPath, ActorSelection, Props, ActorRef}
import persistence.dal._
import slick.backend.DatabaseConfig
import slick.driver.{JdbcProfile}
import persistence.entities._
import slick.lifted.TableQuery


trait Profile {
	val profile: JdbcProfile
}


trait DbModule extends Profile{
	val db: JdbcProfile#Backend#Database
}

trait PersistenceModule {
	val suppliersDal: 						BaseDal[SuppliersTable,					Supplier]
	val asset_groupsDal:					BaseDal[Asset_GroupsTable, 			Asset_Group]
	val asset_owner_groupsDal:		BaseDal[AssetOwnerGroupsTable, 	AssetOwnerGroup]
	val asset_typesDal:						BaseDal[Asset_TypesTable,				Asset_Type]
	val asset_mastersDal:					BaseDal[AssetMasterTable, 			Asset]
	val asset_ownersDal:					BaseDal[AssetOwnersTable,				Asset_Owner]
	val buildingsDal:							BaseDal[BuildingsTable,					Building]
	val manufacturersDal:					BaseDal[ManufacturersTable,			Manufacturer]
	def vendorsDal:								BaseDal[VendorsTable,						Vendor]
	val printersDal: 							BaseDal[PrintersTable, 					Printer]


}


trait PersistenceModuleImpl extends PersistenceModule with DbModule{
	this: Configuration  =>

	// use an alternative database configuration ex:
	 private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig("pgdb")
//	private val dbConfig : DatabaseConfig[JdbcProfile]  = DatabaseConfig.forConfig("h2db")

	override implicit val profile: JdbcProfile = dbConfig.driver
	override implicit val db: JdbcProfile#Backend#Database = dbConfig.db

	override val suppliersDal = new BaseDalImpl[SuppliersTable,Supplier](TableQuery[SuppliersTable]) {}
	override val asset_groupsDal = new BaseDalImpl[Asset_GroupsTable, Asset_Group](TableQuery[Asset_GroupsTable])
	override val asset_owner_groupsDal = new BaseDalImpl[AssetOwnerGroupsTable, AssetOwnerGroup](TableQuery[AssetOwnerGroupsTable]) {}
	override val asset_typesDal = new BaseDalImpl[Asset_TypesTable, Asset_Type](TableQuery[Asset_TypesTable])
	override val asset_mastersDal = new BaseDalImpl[AssetMasterTable, Asset](TableQuery[AssetMasterTable])
	override val asset_ownersDal = new BaseDalImpl[AssetOwnersTable, Asset_Owner](TableQuery[AssetOwnersTable])
	override val buildingsDal = new BaseDalImpl[BuildingsTable, Building](TableQuery[BuildingsTable])
	override val manufacturersDal = new BaseDalImpl[ManufacturersTable, Manufacturer](TableQuery[ManufacturersTable])
	override val vendorsDal = new BaseDalImpl[VendorsTable, Vendor](TableQuery[VendorsTable])
	override val printersDal = new BaseDalImpl[PrintersTable, Printer](TableQuery[PrintersTable])
	val self = this

}
