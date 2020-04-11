package me.retrodaredevil.solarthing.solar.outback.fx;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.retrodaredevil.solarthing.annotations.GraphQLInclude;
import me.retrodaredevil.solarthing.annotations.JsonExplicit;
import me.retrodaredevil.solarthing.packets.Modes;
import me.retrodaredevil.solarthing.solar.SolarStatusPacketType;
import me.retrodaredevil.solarthing.solar.common.BatteryVoltage;
import me.retrodaredevil.solarthing.solar.outback.OutbackStatusPacket;
import me.retrodaredevil.solarthing.solar.outback.fx.common.FXMiscReporter;
import me.retrodaredevil.solarthing.solar.outback.fx.common.FXWarningReporter;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Represents an FX Status Packet from an Outback Mate
 */
@JsonDeserialize(as = ImmutableFXStatusPacket.class)
@JsonTypeName("FX_STATUS")
@JsonExplicit
@JsonClassDescription("Status packet for FX devices")
public interface FXStatusPacket extends OutbackStatusPacket, BatteryVoltage, FXWarningReporter, FXMiscReporter {
	@NotNull
	@Override
	default SolarStatusPacketType getPacketType(){
		return SolarStatusPacketType.FX_STATUS;
	}

	// region Packet Values
	/**
	 * Should be serialized as "inverterCurrentRaw" if serialized at all
	 * @return The raw inverter current.
	 */
	@JsonProperty("inverterCurrentRaw")
	int getInverterCurrentRaw();

	/**
	 * Should be serialized as "chargerCurrentRaw" if serialized at all
	 * @return The raw charger current
	 */
	@JsonProperty("chargerCurrentRaw")
	int getChargerCurrentRaw();

	/**
	 * Should be serialized as "buyCurrentRaw" if serialized at all
	 * @return The raw buy current
	 */
	@JsonProperty("buyCurrentRaw")
	int getBuyCurrentRaw();

	/**
	 * Should be serialized as "inputVoltageRaw" if serialized at all
	 * @return The raw ac input voltage
	 */
	@JsonProperty("inputVoltageRaw")
	int getInputVoltageRaw();

	/**
	 * Should be serialized as "outputVoltageRaw" if serialized at all
	 * @return The raw ac output voltage
	 */
	@JsonProperty("outputVoltageRaw")
	int getOutputVoltageRaw();

	/**
	 * Should be serialized as "sellCurrentRaw" if serialized at all
	 * @return The raw sell current
	 */
	@JsonProperty("sellCurrentRaw")
	int getSellCurrentRaw();

	/**
	 * Should be serialized as "operatingMode"
	 * <p>
	 * FX Operational Mode is the same thing as FX Operating Mode. Although the serialized name is "operatingMode",
	 * "operationalMode" is the recommended name to use
	 * @return The operating mode code which represents a single OperationalMode
	 */
	@JsonProperty("operatingMode")
	int getOperationalModeValue();
	default OperationalMode getOperationalMode(){ return Modes.getActiveMode(OperationalMode.class, getOperationalModeValue()); }

	/**
	 * Should be serialized as "errorMode"
	 * @return The error mode bitmask which represents a varying number of ErrorModes
	 */
	@JsonProperty("errorMode")
	@Override
	int getErrorModeValue();
	@Override
	default @NotNull Set<@NotNull FXErrorMode> getErrorModes(){ return Modes.getActiveModes(FXErrorMode.class, getErrorModeValue()); }

	/**
	 * Should be serialized as "acMode"
	 * @return The AC mode code which represents a single ACMode
	 */
	@JsonProperty("acMode")
	int getACModeValue();
	default @NotNull ACMode getACMode(){ return Modes.getActiveMode(ACMode.class, getACModeValue()); }

	/**
	 * Should be serialized as "misc"
	 * @return The misc mode bitmask which represents a varying number of MiscModes
	 */
	@JsonProperty("misc")
	@Override
	int getMiscValue();

	/**
	 * Should be serialized as "warningMode"
	 * @return The warning mode bitmask which represents a varying number of WarningModes
	 */
	@JsonProperty("warningMode")
	@Override
	int getWarningModeValue();

	/**
	 * Should be serialized as "chksum"
	 * @return The check sum
	 */
	@JsonProperty("chksum")
	int getChksum();
	// endregion

	// region Adjusted Currents and Voltages
	/**
	 * Should be serialized as "inverterCurrent"
	 * @return The inverter current
	 */
	@JsonProperty("inverterCurrent")
	float getInverterCurrent();

	/**
	 * Should be serialized as "chargerCurrent"
	 * @return The charger current
	 */
	@JsonProperty("chargerCurrent")
	float getChargerCurrent();

	/**
	 * Should be serialized as "buyCurrent"
	 * @return The buy current
	 */
	@JsonProperty("buyCurrent")
	float getBuyCurrent();

	/**
	 * Should be serialized as "inputVoltage"
	 * @return The ac input voltage
	 */
	@JsonProperty("inputVoltage")
	int getInputVoltage();

	/**
	 * Should be serialized as "outputVoltage"
	 * @return The ac output voltage
	 */
	@JsonProperty("outputVoltage")
	int getOutputVoltage();

	/**
	 * Should be serialized as "sellCurrent"
	 * @return The sell current
	 */
	@JsonProperty("sellCurrent")
	float getSellCurrent();
	// endregion

	// region Convenience Strings

	/**
	 * Should be serialized as "operatingModeName"
	 * @return The name of the operating mode
	 */
	@JsonProperty("operatingModeName")
	default String getOperatingModeName(){
		return getOperationalMode().getModeName();
	}

	/**
	 * Should be serialized as "errors"
	 * @return The errors represented as a string
	 */
	@JsonProperty("errors")
	@GraphQLInclude("errorsString")
	default @NotNull String getErrorsString() { return Modes.toString(FXErrorMode.class, getErrorModeValue()); }

	/**
	 * Should be serialized as "acModeName"
	 * @return The name of the ac mode
	 */
	@JsonProperty("acModeName")
	default @NotNull String getACModeName() { return getACMode().getModeName(); }

	/**
	 * Should be serialized as "miscModes"
	 * @return The misc modes represented as a string
	 */
	@JsonProperty("miscModes")
	@GraphQLInclude("miscModesString")
	default @NotNull String getMiscModesString() { return Modes.toString(MiscMode.class, getMiscValue()); }

	/**
	 * Should be serialized as "warnings"
	 * @return The warning modes represented as a string
	 */
	@JsonProperty("warnings")
	default @NotNull String getWarningsString() { return Modes.toString(WarningMode.class, getWarningModeValue()); }
	// endregion

	// region Default Power Getters
	@GraphQLInclude("inverterWattage")
	default int getInverterWattage(){
		return getInverterCurrentRaw() * getOutputVoltageRaw();
	}
	@GraphQLInclude("chargerWattage")
	default int getChargerWattage(){
		return getChargerCurrentRaw() * getInputVoltageRaw();
	}
	@GraphQLInclude("buyWattage")
	default int getBuyWattage(){
		return getBuyCurrentRaw() * getInputVoltageRaw();
	}
	@GraphQLInclude("sellWattage")
	default int getSellWattage(){
		return getSellCurrentRaw() * getOutputVoltageRaw();
	}
	// endregion
}
